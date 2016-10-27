# data.uniprot

[![](https://travis-ci.org/bio4j/data.uniprot.svg?branch=master)](https://travis-ci.org/bio4j/data.uniprot) [![](https://img.shields.io/codacy/grade/8ccc1baeccfe4b70a8d7471f448fe57d.svg)](https://www.codacy.com/app/bio4j/data-uniprot) [![](https://api.codacy.com/project/badge/Coverage/8ccc1baeccfe4b70a8d7471f448fe57d)](https://www.codacy.com/app/bio4j/data-uniprot?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=bio4j/data.uniprot&amp;utm_campaign=Badge_Coverage) [![](http://github-release-version.herokuapp.com/github/bio4j/data.uniprot/release.svg)](https://github.com/bio4j/data.uniprot/releases/latest) [![](https://img.shields.io/badge/license-AGPLv3-blue.svg)](https://tldrlegal.com/license/gnu-affero-general-public-license-v3-%28agpl-3.0%29) [![](https://img.shields.io/badge/contact-gitter_chat-dd1054.svg)](https://gitter.im/bio4j/data.uniprot)

An ADT and flat-file parser for UniProt [entries][uniprot-user-manual]. Reasonably fast and with minimal memory overhead, designed for one-entry-at-a-time processing.

### Missing features

- No support for any of the [reference lines][uniprot-reference-lines]; parsers will jsut skip them.
- Some extra information part of some database cross-references is not parsed
- The model for feature types is a bit primitive, lacking type-specific information
- The OG, OX lines are not parsed; see [#9](/../../issues/9) and [#10](/../../issues/10)

[uniprot-user-manual]: http://web.expasy.org/docs/userman.html
[uniprot-reference-lines]: http://web.expasy.org/docs/userman.html#Ref_line
