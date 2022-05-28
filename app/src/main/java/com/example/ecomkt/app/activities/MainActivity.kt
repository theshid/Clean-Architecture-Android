package com.example.ecomkt.app.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import com.example.ecomkt.R
import com.example.ecomkt.app.mappers.toDomain
import com.example.ecomkt.app.models.CommunityInfoPresentation
import com.example.ecomkt.app.viewmodels.MainViewModel
import com.example.ecomkt.databinding.ActivityMainBinding
import com.example.ecomkt.app.util.*
import com.example.ecomkt.app.util.Common.REQUEST_CAMERA
import com.example.ecomkt.app.util.Common.REQUEST_GALLERY
import com.google.android.gms.maps.model.LatLng
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Checked
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Validator.ValidationListener {
    private lateinit var binding: ActivityMainBinding
    var imgInString = ""
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var calendar: Calendar
    private lateinit var validator: Validator
    private var userPosition: LatLng ?= null
    private lateinit var date: OnDateSetListener
    @Checked(message = "You need to upload an image")
    private lateinit var checkBox: CheckBox
    @NotEmpty
    private lateinit var communityName:AppCompatEditText
    @NotEmpty
    private lateinit var geoDistrict:AppCompatEditText
    @NotEmpty
    private lateinit var accessibility:AppCompatEditText
    @NotEmpty
    private lateinit var distance:AppCompatEditText
    @NotEmpty
    private lateinit var connectedToEcg:AppCompatEditText
    @NotEmpty
    private lateinit var latitude:AppCompatEditText
    @NotEmpty
    private lateinit var longitude:AppCompatEditText
    @NotEmpty
    private lateinit var dateEcom:AppCompatEditText


    @OptIn(ExperimentalCoroutinesApi::class)
    private lateinit var fusedLocationWrapper: FusedLocationWrapper

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val extras = intent.extras
        val communityInfoPresentation = extras?.getString(Common.EXTRA_TO_MAIN)
        if (communityInfoPresentation != null) {
            fillUiWithComingData(communityInfoPresentation)
        }
        setUI()
        fusedLocationWrapper = fusedLocationWrapper()
        checkIfPermissionIsActive()
        validator = Validator(this)
        validator.setValidationListener(this)
        hideSoftKeyboard()
        setBtnClickListeners()
        setEditTextClickListeners()
        this.prepareDatePicker()
        PermissionUtils.checkCameraPermissions(this)

    }

    private fun setUI() {
        communityName = binding.communityName
        geoDistrict = binding.geographicalDistrict
        accessibility = binding.accessibility
        distance = binding.distanceEcom
        connectedToEcg = binding.connectedEcg
        latitude = binding.latitude
        longitude = binding.longitude
        dateEcom = binding.licenseDate
        checkBox = binding.checkBox
    }

    private fun fillUiWithComingData(communityInfoPresentation: String) {
        val jsonToCommunityObject = GsonParser.gsonParser?.fromJson(
            communityInfoPresentation,
            CommunityInfoPresentation::class.java
        )
        dateEcom.setText(jsonToCommunityObject?.licenseDate)
        connectedToEcg.setText(jsonToCommunityObject?.connectedToECG)
        geoDistrict.setText(jsonToCommunityObject?.geographicalDistrict.toString())
        distance.setText(jsonToCommunityObject?.distanceToECOM.toString())
        accessibility.setText(jsonToCommunityObject?.accessibility.toString())
        latitude.setText(jsonToCommunityObject?.latitude.toString())
        longitude.setText(jsonToCommunityObject?.longitude.toString())
        communityName.setText(jsonToCommunityObject?.communityName)
        binding.communityPhoto.setImageBitmap(jsonToCommunityObject?.image?.toBitmap())
        if (jsonToCommunityObject?.image?.isNotEmpty() == true) {
            checkBox.text = "Image Loaded"
            checkBox.isChecked = true
        }
    }

    private fun observeViewStates() {
        mainViewModel.insertViewState.observe(this) {
            if (it.isInserted) {
                showToast("Inserted in Database!")
                Timber.d("Inserted in Database!")
            }
            it.error?.let { e ->
                onError(resources.getString(e.message))
            }
        }

        mainViewModel.postResponseViewState.observe(this) {
            it.error?.let { e ->
                onError(resources.getString(e.message))
                showToast("Error posting Community Information")
                Timber.d("Error posting Community Information")
                goBackToListActivity()
            }

            if (it.isComplete && (it.response?.status ?: false) != -1) {
                showToast("Posted Successfully")
                Timber.d("Posted Successfully")
                goBackToListActivity()
            }
        }
    }

    private fun onError(message: String) {
        showToast(message)
    }

    private fun hideSoftKeyboard() {
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    private fun updateDateUI() {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
        dateEcom.setText(sdf.format(calendar.time))
    }

    private fun setEditTextClickListeners() {
        connectedToEcg.setOnClickListener {
            openConnectedToEcgDialog()
        }
        accessibility.setOnClickListener {
            openAccessibilityDialog()
        }
        geoDistrict.setOnClickListener {
            openGeoDistrictDialog()
        }
        distance.setOnClickListener { openDistanceDialog() }
        dateEcom.setOnClickListener { openDatePicker() }
    }

    private fun prepareDatePicker() {
        calendar = Calendar.getInstance()
        date =
            OnDateSetListener { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = monthOfYear
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateDateUI()
            }

    }

    private fun openDatePicker() {
        DatePickerDialog(
            this, date, calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        ).show()
    }

    private fun openDistanceDialog() {
        val distanceArray = resources.getStringArray(R.array.accessibility_numbers)

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.label_connected_ecg)
            .setItems(R.array.ecom_distance) { _, which ->
                when (which) {
                    0 -> distance.setText(distanceArray[5])
                    1 -> distance.setText(distanceArray[6])
                    2 -> distance.setText(distanceArray[7])
                    3 -> distance.setText(distanceArray[8])
                }
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun openGeoDistrictDialog() {
        val districtArray = resources.getStringArray(R.array.accessibility_numbers)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.label_connected_ecg)
            .setItems(R.array.geo_district) { _, which ->
                when (which) {
                    0 -> geoDistrict.setText(districtArray[0])
                    1 -> geoDistrict.setText(districtArray[1])
                    2 -> geoDistrict.setText(districtArray[2])
                }
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun openAccessibilityDialog() {
        //val accessibilityArray = resources.getStringArray(R.array.accessibility_options)
        val accessibilityArrayNumbers = resources.getStringArray(R.array.accessibility_numbers)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.label_connected_ecg)
            .setItems(R.array.accessibility_options) { _, which ->
                when (which) {
                    0 -> accessibility.setText(accessibilityArrayNumbers[0])
                    1 -> accessibility.setText(accessibilityArrayNumbers[1])
                    2 -> accessibility.setText(accessibilityArrayNumbers[2])
                    3 -> accessibility.setText(accessibilityArrayNumbers[3])
                    4 -> accessibility.setText(accessibilityArrayNumbers[4])
                }
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun openConnectedToEcgDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.label_connected_ecg)
            .setItems(R.array.connected_ecg) { _, which ->
                if (which == 0) {
                    connectedToEcg.setText("Y")
                } else {
                    connectedToEcg.setText("N")
                }
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun setBtnClickListeners() {
        binding.submitButton.setOnClickListener { validator.validate() }
        binding.locationButton.setOnClickListener {
            if (userPosition == null) {
                getUserLocation(fusedLocationWrapper)
                latitude.setText(userPosition?.latitude.toString())
                longitude.setText(userPosition?.longitude.toString())
            } else {
                latitude.setText(userPosition?.latitude.toString())
                longitude.setText(userPosition?.longitude.toString())
            }
        }
        binding.addPhoto.setOnClickListener { v -> showPopupMenu(v) }
    }

    private fun showPopupMenu(view: View) {
        // inflate menu
        val popup = PopupMenu(view.context, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.menu_popup, popup.menu)
        popup.setOnMenuItemClickListener(PopupClickListener())
        popup.show()
    }



    private fun sendDataToViewModel(communityInformation: CommunityInfoPresentation) {
        //mainViewModel.saveCommunityToLocalDb(communityInformation)
        mainViewModel.postInfoToServer(communityInformation.toDomain())
    }

    private fun goBackToListActivity() {
        startActivity<ListActivity>()
    }

    private fun getCurrentTimeStamp(): String {
        val sdfDate =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK) //dd/MM/yyyy

        val now = Date()
        return sdfDate.format(now)
    }

    override fun onValidationSucceeded() {
        val communityNameString: String = communityName.text.toString()
        val geoDistrictString: Int = (geoDistrict.text.toString()).toInt()
        val accessibilityString: Int = accessibility.text.toString().toInt()
        val distanceString: Int = distance.text.toString().toInt()
        val connectedToECGString: String = connectedToEcg.text.toString()
        val dateLicenseString: String = dateEcom.text.toString()
        val latitudeString: Double = latitude.text.toString().toDouble()
        val longitudeString: Double = longitude.text.toString().toDouble()
        val createdBy = "murali"
        val createdDate: String = getCurrentTimeStamp()
        val updateBy = ""
        val updateDate = ""

        val communityInformation = CommunityInfoPresentation(
            communityNameString,
            geoDistrictString,
            accessibilityString,
            distanceString,
            connectedToECGString,
            dateLicenseString,
            latitudeString,
            longitudeString,
            imgInString,
            createdBy,
            createdDate,
            updateBy,
            updateDate,
            false
        )

        sendDataToViewModel(communityInformation)
        observeViewStates()
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        for (error in errors!!) {
            val view = error.view
            val message = error.getCollatedErrorMessage(this)

            // Display error messages ;)
            if (view is AppCompatEditText) {
                view.error = message
            } else {
                showToast(message)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun checkIfPermissionIsActive() {
        when {
            PermissionUtils.isAccessFineLocationGranted(this) -> {
                when {
                    PermissionUtils.isLocationEnabled(this) -> {
                        permissionCheck(fusedLocationWrapper)
                    }
                    else -> {
                        PermissionUtils.showGPSNotEnabledDialog(this)
                    }
                }
            }
            else -> {
                PermissionUtils.requestAccessFineLocationPermission(
                    this,
                    Common.LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("MissingPermission")
    private fun permissionCheck(fusedLocationWrapper: FusedLocationWrapper) {
        if (PermissionUtils.isAccessFineLocationGranted(this)) {
            getUserLocation(fusedLocationWrapper)

        } else {
            Toast.makeText(this, getString(R.string.toast_permission), Toast.LENGTH_LONG).show()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("MissingPermission")
    private fun getUserLocation(fusedLocationWrapper: FusedLocationWrapper) {
        this.lifecycleScope.launch {
            val location = fusedLocationWrapper.awaitLastLocation()
            userPosition = LatLng(location.latitude, location.longitude)
        }
    }

    private fun intentGalleryPhotoPicker() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, REQUEST_GALLERY)
    }

    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(intent, REQUEST_CAMERA)
        } catch (e: ActivityNotFoundException) {
            showToast("Camera Unavailable")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_GALLERY) {
            try {
                val imageUri = data!!.data
                val imageStream = contentResolver.openInputStream(imageUri!!)
                val selectedImage = BitmapFactory.decodeStream(imageStream)

                binding.communityPhoto.setImageBitmap(selectedImage)
                imgInString = selectedImage.toBase64()
                checkBox.isChecked = true
                checkBox.setText(R.string.image_loaded)


                //image_view.setImageBitmap(selectedImage);
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        } else if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
                val extras = data!!.extras
                val imageBitmap = extras!!["data"] as Bitmap?
                binding.communityPhoto.setImageBitmap(imageBitmap)
                imgInString = imageBitmap?.toBase64().toString()
                checkBox.isChecked = true
                checkBox.text = "Image has been loaded"
            }
        } else {
            showToast("You haven't picked Image")
        }
    }

    open inner class PopupClickListener : PopupMenu.OnMenuItemClickListener {

        override fun onMenuItemClick(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.action_camera -> {
                    dispatchTakePictureIntent()
                    return true
                }
                R.id.action_gallery -> {
                    intentGalleryPhotoPicker()
                    return true
                }
            }
            return false
        }

    }


}