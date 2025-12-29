#!/usr/bin/env bash
set -e

SRC=src
DST=code-img

find "$SRC" -type f -name '*.java' | while read -r file; do
  out="$DST/${file#$SRC/}"
  mkdir -p "$(dirname "$out")"

  freeze \
    --language java \
    --theme catppuccin-mocha \
    --output "${out%.java}.png" \
    < "$file"
done
