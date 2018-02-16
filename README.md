# Firebase-Push-Notification-Api-
Send push notification from app itself No need of server or any cloud functions.

# Images:
![alt Setting IMages1](https://goo.gl/EjdfHm)
![alt Setting IMages2](https://goo.gl/5CKAZi)

# Prerequisites
- Android 16.
- Google play service on device.

# Warning !
 ###### This library use your firebase cloud mess server key. So, there are two way to use it,
 - Not use hard coded server key in project, store it to your database and get it from there. then is safe to  use.
 - otherwise, you can put into your string file and get it from there, but it is risky.
 
 # Installing
## Step 1:- Add it in your root build.gradle at the end of repositories:
````
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
````
## Step 1:- Add the dependency:
````

		dependencies {
	        compile 'com.github.prabhat1707:Firebase-Push-Notification-Api-:1.0'
	}
	
  
````

# Usage

you can send notification to other device by using thos api or library. just follow this simple step. Send small image , large image 
any imformation to notification.

#### Option 1:- Use default Json, in this you can only pas title or message to your notification.

Also you need firebase token, which you will get in your firebaseIDService.
All this passing data i.e- title and mess are rec in your FirebaseMessService, where you will get it and make notification. 

````
 FirebaseNotificationHelper.initialize(getString(R.string.server_key))
                        .defaultJson(true, null)
                        .title("Test")
                        .message("Test Mess")
                        .setCallBack(MainActivity.this)
                        .receiverFirebaseToken(SharedPrefUtil.getInstance(MainActivity.this).getString(FIREBASE_TOKEN))
                        .send();
````

#### Option 2: Use Custom Json, it mean make your own json object and pass it to default json method.

````
 FirebaseNotificationHelper.initialize(getString(R.string.server_key))
                        .defaultJson(false, getJsonBody())
                        .setCallBack(MainActivity.this)
                        .receiverFirebaseToken(SharedPrefUtil.getInstance(MainActivity.this).getString(FIREBASE_TOKEN))
                        .send();
                       
                        
                         private String getJsonBody() {

        JSONObject jsonObjectData = new JSONObject();
        try {
            jsonObjectData.put(KEY_TITLE, "Custom Title");
            jsonObjectData.put(KEY_TEXT, "Custom Mess");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonObjectData.toString();
    }
   
````

#### CallBack Method are optional, if you want then implement it "implements FirebaseNotiCallBack"

````

@Override
    public void success(String s) {
        Log.i(Constants.TAG, "----------->" + s);
        Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fail(String s) {
        Log.i(Constants.TAG, "----------->" + s);
        Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();

    }
````
###### For more see Sample Project.

# Important

At time of rec notification you will get json also with the same key as you use at time of sending. So, retrive by using same key.
Some key are in sample project for default json and for custom use according to you.

#### For Any Query Mail me to "prabhatrai@trenzlr.com"
#### Thanks 

# License

````
Copyright (c) delight.im <prabhatrai@trenzlr.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable la
w or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

