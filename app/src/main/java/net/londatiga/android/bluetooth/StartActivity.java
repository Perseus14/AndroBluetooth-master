package net.londatiga.android.bluetooth;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import android.app.AlertDialog;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;



import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.londatiga.android.bluetooth.adapter.NavDrawerListAdapter;
import net.londatiga.android.bluetooth.model.NavDrawerItem;

import java.util.ArrayList;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import java.util.Set;
import java.util.UUID;


public class StartActivity extends Activity {

    private static final int NUM_PAGES=1;

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    private UUID BTMODULEUUID;
    private static final UUID MY_UUID_SECURE =
            UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    private TextView text1;
    private Button button,send;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mDevice;
    private ConnectedThread mConnectedThread;
    private Handler mHandler;
    private int index;
    private AlertDialog levelDialog;
    private Set<BluetoothDevice>pairedDevices;
    private BluetoothSocket btSocket;
    private Speedometer speedometer;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Intent intent = getIntent();
        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));


        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }


  //      text1 = (TextView) findViewById(R.id.textView1);
        //etext = (EditText) findViewById(R.id.etext);
    //    button = (Button) findViewById(R.id.button);
        //send = (Button) findViewById(R.id.send);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
      //  speedometer = (Speedometer) findViewById(R.id.Speedometer);

        BTMODULEUUID = UUID.fromString("11b3fe48-fe9a-431a-8667-e78c9055742b");
/*        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mConnectedThread.cancel();
            }
        });*/

        /*send.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view)
            {
                String k  = etext.getText().toString();
                if (k.trim().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Empty Command", Toast.LENGTH_LONG).show();
                    return;
                } else
                {
                    //writeToTerm((new StringBuilder()).append("> ").append(view).append("\n").toString());
                    mConnectedThread.write(k+"\r");
                    etext.setText("");
                    return;
                }
            }
        });*/

        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth Not Available", Toast.LENGTH_LONG).show();
        }
        else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }

            pairedDevices = mBluetoothAdapter.getBondedDevices();

            if (pairedDevices.size() > 0) {
                if (pairedDevices.size() == 1) {
                    for (BluetoothDevice device : pairedDevices) {
                        mDevice = device;
                    }
                    Toast.makeText(getApplicationContext(), mDevice.getName() + String.valueOf(pairedDevices.size()), Toast.LENGTH_SHORT).show();
                    running();
                }
                else {
                    init();
                }
            }

            else {
                Toast.makeText(getApplicationContext(), "No paired Devices", Toast.LENGTH_LONG).show();
            }
        }
    }
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(MY_UUID_INSECURE);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        disconnect();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

    }

    public void init()
    {
        String [] items = new String [pairedDevices.size()];
        int j=0;

        for(BluetoothDevice device: pairedDevices)
        {
            items[j]=device.getName();
            j++;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select the Device");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                index = item;

                int i = 0;
                for (BluetoothDevice device : pairedDevices) {
                    if (i == index) {
                        mDevice = device;
                        break;
                    }
                    i++;
                }
                levelDialog.dismiss();
                running();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }
    private int pos = 0;
    private String str="",prev="-2",temp="-1",now="0",speed="",rpm="",Etemp="",Acc="",ELoad="";
    private int count=0;
    private Double acc=0.0;
    private Double EL=0.0;
    private Long ECT=0L;
    private Long L=0L;
    private Double Ri=0.0;
    public void running()
    {
        Toast.makeText(getApplicationContext(), mDevice.getName() + String.valueOf(pairedDevices.size()), Toast.LENGTH_SHORT).show();

        mHandler = new Handler() {

            public void handleMessage(Message message) {
/*                super.handleMessage(msg);
                byte[] writeBuf = (byte[]) msg.obj;
                String s = writeBuf.toString();
                text1.setText(s);
                // writeBuf = (String) msg.obj;
                int begin = (int) msg.arg1;
                int end = (int) msg.arg2;
                switch (msg.what) {
                    case 1:
                        String writeMessage = new String(writeBuf);
                        //writeMessage = writeMessage.substring(begin, end);
                        text1.setText(writeMessage);
                        break;
                }*/
                super.handleMessage(message);
                byte[] message1 = (byte[])message.obj;
                int j = message1.length;
                String str1="",s="";
                for (int i = 0; i < j; i++)
                {
                    if (message1[i] == 0)
                    {
                        continue;
                    }
                    s = (Character.valueOf((char)message1[i])).toString();
                    prev=temp;
                    temp=now;
                    now=s;
                    str+=s;
                    //Log.d("String",prev+temp+now);
                    if((prev+temp+now).trim().equals("E7D")==true)
                    {

                        str=str.trim();

                        Log.d("String",str);
                        if(count>0 && str.length()>=270 ) {
                            speed = str.substring(265,270);
                            rpm = str.substring(15,20);
                            rpm = rpm.substring(0,2) + rpm.substring(3);
                            Etemp= str.substring(232,234);
                            Acc=str.substring(196,201);
                            ELoad = Acc.substring(3);
                            Acc=Acc.substring(0,2);
                            acc=(Long.parseLong(Acc,16)/255.0)*100;
                            EL=(Long.parseLong(ELoad,16)/255.0)*100;
                            ECT=Long.parseLong(Etemp,16)-4;
                            Ri = Long.parseLong(rpm, 16)/4.0;
                            speed = speed.substring(0,2) + speed.substring(3);
                            Log.d("Speed", speed);
                            L = Long.parseLong(speed, 16);
                            if(mDrawerLayout.isDrawerOpen(mDrawerList)==false)
                                displayView(pos);

                            //text1.setText("Speed = " + String.valueOf(L) + " RPM = " + String.valueOf(R));
//                            speedometer.onSpeedChanged(L);
                        }
                        count=1;
                        str="E7D";
                        prev="-2";
                        temp="-1";
                        now="0";

                    }
                    if(s!=">")
                        str1+=s;
                    if (message1[i] == 62)
                    {
                        str+="\n";
                        str1+="\n";
                    }
                }
                //text1.setText(str);
                Log.d("RDS",str);
            }
        };
        try {
            btSocket = createBluetoothSocket(mDevice);
            Toast.makeText(getBaseContext(), "Socket creation success", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }

        connect();

        Log.d("Log", "Started");
        new CountDownTimer(35000, 1000) {
            int i = 35;
            public void onFinish() {
               //mConnectedThread.write("at ma\r");
                //Log.d("Log","35 sec");
            }

            public void onTick(long millisUntilFinished) {
                //Log.d("count",String.valueOf(i--));
            }
        }.start();

    }

    void connect()
    {
        try
        {
            mBluetoothAdapter.cancelDiscovery();
            btSocket.connect();
        } catch (IOException e) {

            try {
                Toast.makeText(getBaseContext(), "Falling back", Toast.LENGTH_LONG).show();

                btSocket =(BluetoothSocket) mDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(mDevice,1);
                btSocket.connect();

                Toast.makeText(getBaseContext(), "Connected Falling back", Toast.LENGTH_LONG).show();
            }
            catch (Exception e2) {
                Toast.makeText(getBaseContext(), "Socket connection failed", Toast.LENGTH_LONG).show();
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);

        mConnectedThread.start();
        mConnectedThread.write("at i\r");
        mConnectedThread.write("at z\r");
        mConnectedThread.write("at sp9\r");
        delay("at ma\r");
    }

    void disconnect()
    {
        try {
            btSocket.close();
        }
        catch (IOException e)
        {
            Log.d("SD","No socket to close");
        }
        catch (NullPointerException e)
        {
            Log.d("SD","NullPointerException");
        }
    }

    void delay(String s)
    {
        final String k = s;
        new CountDownTimer(10000, 1000) {
            public void onFinish() {
                mConnectedThread.write(k);
            }

            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
            }
        }.start();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"IOException",Toast.LENGTH_LONG).show();
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        public void run() {
            while (true) {
                try {
                    byte abyte0[];
                    do
                    {
                        abyte0 = new byte[10240];
                    } while (mmInStream.read(abyte0) <= 0);
                    Message message = new Message();
                    message.obj=abyte0;
                    mHandler.sendMessage(message);
                } catch (IOException e) {
                    break;
                }
            }
        }
        public void write(String s) {
            try {
                mmOutStream.write(s.getBytes());
                Log.d("WD", s);

            } catch (IOException e) {
                String k = e.getMessage();
                Toast.makeText(getBaseContext(),k + this.toString() , Toast.LENGTH_LONG).show();
//                text1.setText("Null");
                disconnect();
                //connect();

            }
        }
        public void cancel() {
            try {
                disconnect();
                mmSocket.close();
                finish();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"Cannot Close Context",Toast.LENGTH_LONG).show();
            }
        }

        public String toString()
        {
            if(this == null)
               return "Object is NULL";
            return "Something is Connected";
        }
    }

    /* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */


    private void displayView(int position) {
        // update the main content by replacing fragments
        android.app.Fragment fragment = null;


            switch (position) {
                case 0:
                    fragment = SpeedFragment.newInstance(L);
                    break;
                case 1:
                    fragment = RPMFragment.newInstance(Ri);
                    break;
                case 2:
                    fragment = AccelerationFragment.newInstance(acc);
                    break;
                case 3:
                    fragment = ELFragment.newInstance(EL);
                    break;
                case 4:
                    fragment = FuelFragment.newInstance(L);
                    break;
                case 5:
                    fragment = ETFragment.newInstance(ECT);
                    break;

                default:
                    break;

            }
            pos=position;
        if (fragment != null) {
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }


}
