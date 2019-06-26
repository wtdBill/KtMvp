package ktmvp.yppcat.ktmvp.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Environment
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_bule_tooth.*
import ktmvp.yppcat.ktmvp.R
import ktmvp.yppcat.ktmvp.adapter.BlueToothAdapter
import ktmvp.yppcat.ktmvp.base.BaseActivity
import ktmvp.yppcat.ktmvp.data.IntentName
import ktmvp.yppcat.ktmvp.utils.Logger
import ktmvp.yppcat.ktmvp.utils.showToast
import pub.devrel.easypermissions.EasyPermissions
import java.io.*
import java.util.*

@Route(path = IntentName.APP_ACTIVITY_BLUETOOTH)
class BlueToothActivity : BaseActivity() {

    private lateinit var mBlueToothAdapter: BluetoothAdapter
    private lateinit var mBlueToothManager: BluetoothManager
    private var mDeviceArray = arrayListOf<BluetoothDevice>()
    private var mBoundDeviceArray = arrayListOf<BluetoothDevice>()
    private var mUnBoundDeviceArray = arrayListOf<BluetoothDevice>()

    private lateinit var mDeviceAdapter: BlueToothAdapter
    private lateinit var mBoundAdapter: BlueToothAdapter
    private lateinit var mUnBoundAdapter: BlueToothAdapter

    private lateinit var mBlueToothFilter: IntentFilter

    private lateinit var mBlueToothSocket: BluetoothSocket

    private lateinit var mServerSocket: BluetoothServerSocket

    override fun layoutId(): Int {
        return R.layout.activity_bule_tooth
    }

    override fun initData() {
        mBlueToothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBlueToothAdapter = mBlueToothManager.adapter
        if (!mBlueToothAdapter.isEnabled) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, 1)
        }
        val perms = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        EasyPermissions.requestPermissions(this, "", 0, *perms)
    }

    override fun initView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mDeviceAdapter = BlueToothAdapter(R.layout.item_bluetooth, mDeviceArray)
        device_recycle.layoutManager = layoutManager
        device_recycle.adapter = mDeviceAdapter

        val layoutManager1 = LinearLayoutManager(this)
        layoutManager1.orientation = LinearLayoutManager.VERTICAL
        mBoundAdapter = BlueToothAdapter(R.layout.item_bluetooth, mBoundDeviceArray)
        bound_recycle.layoutManager = layoutManager1
        bound_recycle.adapter = mBoundAdapter

        val layoutManager2 = LinearLayoutManager(this)
        layoutManager2.orientation = LinearLayoutManager.VERTICAL
        mUnBoundAdapter = BlueToothAdapter(R.layout.item_bluetooth, mUnBoundDeviceArray)
        unbound_recycle.layoutManager = layoutManager2
        unbound_recycle.adapter = mUnBoundAdapter

        mDeviceAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, _, position -> boundService(mDeviceArray[position]) }

        initSearchBroadCast()
    }

    override fun start() {
        search.setOnClickListener {
            if (mBlueToothAdapter.isEnabled) {
                if (!mBlueToothAdapter.isDiscovering) {
                    mBlueToothAdapter.startDiscovery()
                }
            }
        }
    }

    private fun initSearchBroadCast() {
        mBlueToothFilter = IntentFilter()
        mBlueToothFilter.addAction(BluetoothDevice.ACTION_FOUND)
        mBlueToothFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        mBlueToothFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        mBlueToothFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        mBlueToothFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        mBlueToothFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST)
        registerReceiver(blueToothReceiver, mBlueToothFilter)
    }

    private val blueToothReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> Logger.e("正在扫描设备......")
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> Logger.e("设备扫描完毕.....")
                BluetoothDevice.ACTION_FOUND -> findDevice(intent)
                BluetoothDevice.ACTION_BOND_STATE_CHANGED -> bound(intent)
                BluetoothAdapter.ACTION_STATE_CHANGED -> changeState()
            }
        }

    }

    private fun changeState() {
        when (mBlueToothAdapter.state) {
            BluetoothAdapter.STATE_OFF -> Logger.e("蓝牙状态：关闭")
            BluetoothAdapter.STATE_ON -> Logger.e("蓝牙状态：打开")
            BluetoothAdapter.STATE_TURNING_ON -> Logger.e("蓝牙状态：正在打开")
            BluetoothAdapter.STATE_TURNING_OFF -> Logger.e("蓝牙状态：正在关闭")
        }
    }

    private fun bound(intent: Intent) {
        val mDevice = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
        when (mDevice.bondState) {
            BluetoothDevice.BOND_BONDING -> Logger.e("蓝牙正在配对......")
            BluetoothDevice.BOND_BONDED -> {
                mBoundDeviceArray.add(mDevice)
                if (mDevice in mUnBoundDeviceArray) {
                    mUnBoundDeviceArray.remove(mDevice)
                }
                Logger.e("蓝牙完成配对......")
            }
            BluetoothDevice.BOND_NONE -> {
                mUnBoundDeviceArray.add(mDevice)
                if (mDevice in mBoundDeviceArray) {
                    mBoundDeviceArray.remove(mDevice)
                }
                Logger.e("蓝牙取消配对......")
            }
        }
    }

    private fun findDevice(intent: Intent) {
        val mDevice = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
        Logger.e("扫描到设备${mDevice.name}")
        if (mDevice !in mDeviceArray) {
            mDeviceArray.add(mDevice)
            mDeviceAdapter.notifyDataSetChanged()
        }
    }

    private fun boundService(bluetoothDevice: BluetoothDevice) {
        if (mBlueToothSocket.isConnected) {
            if (mBlueToothSocket.remoteDevice == bluetoothDevice) {
                removeBound(bluetoothDevice)
            }
        }
        val method = BluetoothDevice::class.java.getMethod("createBond")
        method.invoke(bluetoothDevice)
        Logger.e("开始配对")
        connect(bluetoothDevice)
    }

    private fun removeBound(bluetoothDevice: BluetoothDevice) {
        val method = BluetoothDevice::class.java.getMethod("removeBound")
        method.invoke(bluetoothDevice)
        Logger.e("取消配对")
    }

    @SuppressLint("CheckResult")
    private fun connect(bluetoothDevice: BluetoothDevice) {
        mBlueToothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
        if (mBlueToothAdapter.isDiscovering) {
            mBlueToothAdapter.cancelDiscovery()
        }
        if (!mBlueToothSocket.isConnected) {
            Observable.create<Void> {
                mBlueToothSocket.connect()
            }

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                showToast("蓝牙开启成功")
                initSearchBroadCast()
            } else {
                showToast("没有蓝牙权限")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(blueToothReceiver)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        super.onPermissionsGranted(requestCode, perms)
        if (requestCode == 0) {
            if (perms.isNotEmpty()) {
                if (perms.contains(Manifest.permission.ACCESS_COARSE_LOCATION) && perms.contains(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    start()
                }
            }
        }
    }

    private fun sendMessage(message: String) {
        if (message.isEmpty()) {
            return
        }
        try {
            val msg = message + "\n"
            val outputStream = mBlueToothSocket.outputStream
            outputStream.write(msg.toByteArray(Charsets.UTF_8))
            outputStream.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun sendFile(filePath: String) {
        if (filePath.isEmpty()) {
            return
        }
        try {
            val file = File(filePath)
            if (!file.exists() || file.isDirectory) {
                return
            }
            val outputStream = mBlueToothSocket.outputStream
            outputStream.write("file".toByteArray(Charsets.UTF_8))
            val inputStream = FileInputStream(file)
            val b = ByteArray(1024)
            var length: Int
            var fileSize = 0
            while (inputStream.read(b) != -1) {
                length = inputStream.read(b)
                fileSize += length
                outputStream.write(b, 0, length)
            }
            inputStream.close()
            outputStream.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    //以下服务端接收

    private fun startBlueService(){
        while (true){
//            try {
//                val bluetoothSocket = mBlueToothAdapter.listenUsingRfcommWithServiceRecord("server", UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
//                mServerSocket = bluetoothSocket
//            }
        }
    }

    private fun receiveMessage() {
        try {
            val inputStream = mBlueToothSocket.inputStream
            val bff = BufferedReader(InputStreamReader(inputStream))
            var json: String
            while (true) {
                while (bff.readLine() != null) {
                    json = bff.readLine()
                    if ("file" == json) {
                        val fos = FileOutputStream(File(Environment.getExternalStorageDirectory().path + "/test.gif"))
                        var length = 0
                        var fileSize = 0
                        val b = ByteArray(1024)
                        while (inputStream.read(b) != -1) {
                            length = inputStream.read(b)
                            fos.write(b, 0, length)
                            fileSize += length
                            Logger.e("大小$fileSize")
                        }
                        fos.close()
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
