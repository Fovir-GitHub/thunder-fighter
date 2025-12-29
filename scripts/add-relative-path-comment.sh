#!/usr/bin/env bash
set -e

SRC=src

find "$SRC" -type f -name '*.java' | while read -r file; do
  rel="${file#./}"

  tmp="$(mktemp)"
  printf "// %s\n\n" "$rel" > "$tmp"
  cat "$file" >> "$tmp"
  mv "$tmp" "$file"
done
