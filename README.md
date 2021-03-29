# DIAnalyzer

Software analysis for Java dependency injection anti-patterns.

## Documentation & Instructions

The instructions and project documentation are found in the [docs](docs/Programming_Conclusion_Project.pdf) folder.

### Prerequisites

Maven and JDK >= 8

## Built With

* [Maven](https://maven.apache.org) - Dependency Management
* [JavaParser](https://javaparser.org) - Parser for the Java language
* [RepoDriller](https://github.com/mauricioaniche/repodriller) - Java framework to support mining software repositories
* [Apache POI](https://poi.apache.org) - Java API for Microsoft Documents
* [Google Guava](https://github.com/google/guava) - Google Core Libraries for Java
* [JUnit](https://junit.org) - Testing framework for Java and the JVM
* [GitHub API for Java](https://github-api.kohsuke.org/) - Library that defines an object oriented representation of the GitHub API

## Authors

* **Rodrigo Laigner** - [DIAnalyzer](https://github.com/rnlaigner/dianalyzer)

## Reference

Please reference the following work when referring to DIAnalyzer:

```
@inproceedings{10.1145/3350768.3350771,
author = {Laigner, Rodrigo and Kalinowski, Marcos and Carvalho, Luiz and Mendon\c{c}a, Diogo and Garcia, Alessandro},
title = {Towards a Catalog of Java Dependency Injection Anti-Patterns},
year = {2019},
isbn = {9781450376518},
publisher = {Association for Computing Machinery},
address = {New York, NY, USA},
url = {https://doi.org/10.1145/3350768.3350771},
doi = {10.1145/3350768.3350771},
booktitle = {Proceedings of the XXXIII Brazilian Symposium on Software Engineering},
pages = {104–113},
numpages = {10},
keywords = {inversion of control, java, catalog, anti-pattern, dependency injection, coupling, dependency inversion, modularization},
location = {Salvador, Brazil},
series = {SBES 2019}
}

@mastersthesis{LaignerMaster20,
  author    = {Rodrigo Nunes Laigner}, 
  title     = {Cataloging Dependency Injection Anti-Patterns in Software Systems},
  school    = {Pontifical Catholic University of Rio de Janeiro (PUC-Rio)},
  year      = {2020},
  doi = {https://doi.org/10.17771/PUCRio.acad.47298},
  url       = {https://www.maxwell.vrac.puc-rio.br/48681/48681.PDF}
}
```

## Credits

[Template](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
