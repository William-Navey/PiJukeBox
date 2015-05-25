*** PRE-REQS ***

You'll need to:

1. Create a twitter account, or use an existing, to be used as the "JukeBox" account. You will need to obtain the twitter oath credentails from this account. Modify the file src/main/resoures/twitter_oath_credentials.json with your appropriate oath credentials. Modify the "twitter_handle" value in src/main/resources/jukebox_config.json to your twitter account handle (ex: @MyJukebox)

2. Create a google account, or use an existing. to be used to play the YouTube videos from. You will need to obtain the google oath credentails from this account. Modify the file src/main/resources/google_client_secrets.json with your appropriate credentials.

3. Setup a browser to be able to be properly leveraged by this software. Follow the BROWSER PREREQS steps of a browser of your choice below:


BROWSER PREREQS:
-----------------------------------

For FIREFOX:

1. Install Firefox
2. install add-block firefox plugin
3. install adobe flash firefox plugin (some youtube videos require flash
4. in Firefox, type "about:config" in url bar, filter with "resume", and change browser.sessionstore.resume_from_crash to false
5. In the src/main/resources/jukebox_config.json, ensure that the browser_path value is accurate



For CHROME:

I don't know yet, just use firefox
-----------------------------------



*** Tips! ***

