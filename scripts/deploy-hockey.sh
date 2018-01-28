#!/bin/sh

# upload apk to hockey app
curl \
-F "status=2" \
-F "notify=0" \
-F "ipa=@app/build/outputs/apk/release/app-release.apk" \
-H "X-HockeyAppToken: 7687cb6abe9244baa8f83acfaa4e88d5" \
https://rink.hockeyapp.net/api/2/apps/c21da4f96513481cb7813f3e397d4546/app_versions/upload