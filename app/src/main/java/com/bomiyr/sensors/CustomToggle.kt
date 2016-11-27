package com.bomiyr.sensors

import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Switch


/**
 * Created by Misha on 19.11.2016.
 */
class CustomToggle : LinearLayout {

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs)
    }

    var infoText = ""

    private fun init(attrs: AttributeSet?) {
        orientation = HORIZONTAL

        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.custom_toggle, this, true)

        infoView = findViewById(R.id.info)
        switchView = findViewById(R.id.toggle) as Switch?


        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomToggle, 0, 0)
        infoText = a.getString(R.styleable.CustomToggle_info)
        val prefKey = a.getString(R.styleable.CustomToggle_preference_key)
        val text = a.getString(R.styleable.CustomToggle_android_text)
        a.recycle()

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val enabled = prefs.getBoolean(prefKey, false)
        switchView?.setOnCheckedChangeListener { button, checked -> prefs.edit().putBoolean(prefKey, checked).apply() }
        switchView?.isChecked = enabled
        switchView?.text = text

        infoView?.setOnClickListener({ v ->
            InfoDialogFragment(title, infoText).show(getActivity()?.supportFragmentManager, "info_dialog")
        })
    }

    var title: String
        get() = if (switchView == null) "" else switchView?.text.toString()
        set(value) {
            switchView?.text = value
        }

    var enabled: Boolean?
        get() = switchView?.isEnabled
        set(value) {
            switchView?.isEnabled = value as Boolean
        }


    private var infoView: View? = null
    private var switchView: Switch? = null


    fun getActivity(): AppCompatActivity? {
        var context = context
        while (context is ContextWrapper) {
            if (context is AppCompatActivity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }


}

class InfoDialogFragment : DialogFragment {

    constructor() : super()
    constructor(title: String, info: String) : this() {
        val args = Bundle()
        args.putString("arg_title", title)
        args.putString("arg_info", info)

        arguments = args
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val title: String = arguments.getString("arg_title")
        val info: String = arguments.getString("arg_info")

        val builder = AlertDialog.Builder(context)
        return builder.setTitle(title).setMessage(info).create()
    }

}
