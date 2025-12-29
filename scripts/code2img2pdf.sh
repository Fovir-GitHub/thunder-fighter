#!/usr/bin/env bash
set -e

SRC=src
DST=code-img

find "$SRC" -type f -name '*.java' | while read -r file; do
  out="$DST/${file#$SRC/}"
  mkdir -p "$(dirname "$out")"

  png="${out%.java}.png"

  freeze \
    --language java \
    --theme catppuccin-mocha \
    --output "$png" \
    < "$file"

  pngquant --quality=60-80 --ext .png --force $png
done

find "$DST" -type f -name '*.png' -print0 | sort -z | xargs -0 img2pdf --output code.pdf
