#!/bin/bash

# This script needs inkscape.

ICONS_DIR=.icons
SVG=images/ic_launcher.svg

function update() {
    local name=$1
    local to_dir=$2
    mkdir -p ${ICONS_DIR}/${name}
    sed -e "s|snippets|${name}|" ${SVG} > ${ICONS_DIR}/ic_launcher_${name}.svg
    export_icons -t Android -i ${ICONS_DIR}/ic_launcher_${name}.svg -o ${ICONS_DIR}/${name} -v
    cp -pR ${ICONS_DIR}/${name}/Android/res ${to_dir}/
}

if [ -d ${ICONS_DIR} ]; then
    rm -rf ${ICONS_DIR}
fi

update "snippets" app-simplealertdialog/src/main
update "AFE" app-androidformenhancer/src/main
update "flavors" app-flavor/src/main
update "Rb" app-richbuttons/src/main
update "SAD" app-simplealertdialog/src/main

rm -rf ${ICONS_DIR}

