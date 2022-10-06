#!/bin/bash

. ~/.config/secrets/modrinth.sh
. ~/.config/secrets/curseforge.sh

pushd . 

cd $(mktemp -d /tmp/libIPN-release.XXXX)

git clone --recurse-submodules git@github.com:blackd/IPNRejects.git code

cd code/description

#python build_html.py
python build_release_notes.py

cd ..

./gradlew --max-workers 32 clean build publishMavenPublicationToMavenRepository modrinth curseforge


ls -la build/lib/

pwd

popd
