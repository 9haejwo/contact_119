package com.android.contact_119

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.WindowManager.LayoutParams
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.android.contact_119.data.ContactItems
import com.android.contact_119.databinding.FragmentDialogBinding
import com.android.contact_119.fragment.ContactFragment
import com.android.contact_119.manager.BUSAN
import com.android.contact_119.manager.DAEGU
import com.android.contact_119.manager.DAEJEON
import com.android.contact_119.manager.GWANGJOO
import com.android.contact_119.manager.INCHEON
import com.android.contact_119.manager.SEOUL
import com.android.contact_119.manager.ULSAN
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class DialogFragment : DialogFragment() {
    private lateinit var binding: FragmentDialogBinding
    private var contactDataListener: ContactDataListener? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogBinding.inflate(inflater)

        return binding.root
    }

    override fun onResume() {
        //디바이스 가로 사이즈의 90% 설정
        val windowManager =
            requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.9).toInt()
        dialog?.window?.attributes = params as LayoutParams
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            checkPhoneNumber(etContact, ContactInputLayout)
            check(etName, NameInputLayout)
            check(etAddress, AddressInputLayout)
            spinner.adapter = context?.let {
                ArrayAdapter.createFromResource(
                    it,
                    R.array.area,
                    android.R.layout.simple_list_item_1
                )
            }


            btnAdd.setOnClickListener {
                val nameEmpty = etName.text!!.isEmpty()
                val addressEmpty = etAddress.text!!.isEmpty()
                val nameBlank = etName.text!!.isBlank()
                val addressBlank = etAddress.text!!.isBlank()
                val contactEmpty = etContact.text!!.isEmpty()
                //빈 값 있는지 체크
                if (nameEmpty || contactEmpty || addressEmpty || !checkNumberLength(
                        etContact
                    ) || nameBlank || addressBlank || NameInputLayout.error != null || ContactInputLayout.error != null || AddressInputLayout.error != null
                ) {
                    if (nameEmpty || nameBlank || NameInputLayout.error != null) NameInputLayout.error =
                        getString(R.string.Name_error_message) else NameInputLayout.error = null
                    if (contactEmpty || !checkNumberLength(etContact) || ContactInputLayout.error != null) ContactInputLayout.error =
                        getString(R.string.Contact_error_message) else ContactInputLayout.error =
                        null
                    if (addressEmpty || addressBlank || AddressInputLayout.error != null) AddressInputLayout.error =
                        getString(R.string.Address_error_message) else AddressInputLayout.error =
                        null

                    return@setOnClickListener
                } else {
                    val area = when (spinner.selectedItemPosition) {
                        0 -> SEOUL
                        1 -> BUSAN
                        2 -> DAEJEON
                        3 -> DAEGU
                        4 -> INCHEON
                        5 -> GWANGJOO
                        6 -> ULSAN
                        else -> SEOUL
                    }
                    contactDataListener?.onContactDataAdded(
                        etName.toText(),
                        etContact.toText(),
                        etAddress.toText(),
                        area,
                    )
                    dialog?.dismiss()
                }

            }
            btnCancel.setOnClickListener {
                dialog?.dismiss()
            }
        }
    }

    private fun TextInputEditText.toText(): String {
        return text.toString()
    }

    //전화번호 유효성 체크
    private fun checkPhoneNumber(editText: EditText, layout: TextInputLayout) {
        editText.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (editText.text.isEmpty() || editText.text.isBlank() || editText.text.contains(" ")) layout.error =
                    getString(R.string.Contact_error_message)
                else if (editText.text[0] != '0' && editText.text[0] != '1') layout.error =
                    getString(R.string.Contact_error_message)
                else if (!editText.text.contains("-")) layout.error =
                    getString(R.string.Contact_error_message)
                else layout.error = null
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    //시작 번호가 0일때, 1일때 자리수 체크
    private fun checkNumberLength(editText: EditText): Boolean {
        var state = false
        if (editText.text[0] == '0') {
            if (editText.text.length == 12) state = true
        } else if (editText.text[0] == '1') {
            if (editText.text.length == 9 && editText.text.contains("-")) state = true
        }
        return state
    }

    //병원명, 주소 유효성 체크
    private fun check(editText: EditText, layout: TextInputLayout) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (editText.text.isEmpty() || editText.text.isBlank() || editText.text.contains(" ")) {
                    layout.error = "${editText.hint}${getString(R.string.check_footer)}"
                } else {
                    layout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

    }

    fun setContactDataListener(listener: ContactFragment) {
        contactDataListener = listener
    }
}

interface ContactDataListener {
    fun onContactDataAdded(name: String, phoneNumber: String, address: String, location: String)
}
