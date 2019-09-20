# FIAMDemo
How to reproduce the issue that 'triggerEvent not working when the app launh'?
* Launch FIAM-Demo App;
* Click the buttons to show In-App Messages.
* As each message can only show once per deviece, if you want show again, 
can click 'Clear Data' button to re-Launch the app, then try again to click buttons to show messages.


FYI, if delete the code 'finish()' in WelcomeActivity, all works well .

```
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"activity on create call------");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //finish(); // delete or not, can reproduce the issue.
    }
```
