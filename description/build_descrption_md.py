#!/usr/bin/env python3
#-*- coding: utf-8 -*-

import pypandoc
from pathlib import Path

def main():
    Path("out/debug").mkdir(parents=True, exist_ok=True)

    html = pypandoc.convert_file(
        "description.md",
        "md",
        format="md",
        filters=["pandoc-include"]
    )

    with open("out/description.md", "wb") as f:
        f.write(html.encode("utf-8"))


if __name__ == "__main__":
    main()