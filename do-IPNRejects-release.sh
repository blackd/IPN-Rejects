#!/bin/bash

. ~/.config/secrets/modrinth.sh
. ~/.config/secrets/curseforge.sh

pushd . 

cd $(mktemp -d /tmp/IPN-Rejects-release.XXXX)

git clone --recurse-submodules git@github.com:blackd/IPN-Rejects.git code

cd code/description

#python build_html.py
python build_release_notes.py

cd ..

./gradlew --max-workers 32 clean build modrinth curseforge


ls -la build/lib/

pwd

popd
