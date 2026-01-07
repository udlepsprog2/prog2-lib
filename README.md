# prog2-lib

A small Java library used by the Escola Politècnica Superior (EPS) of the Universitat de Lleida (UdL) for the course "Programació 2". This repository contains a minimal, simplified re-implementation of selected APIs inspired by the ACM Java Task Force libraries.

Important note
--------------
This project re-implements a very small and simplified subset of some APIs from the ACM Java Task Force (ACM Java Libraries). The original ACM project appears unmaintained since 2008; its historical page is available at: https://cs.stanford.edu/people/eroberts/jtf/.

The intent of this re-implementation is educational: to provide a lightweight, easy-to-inspect set of utilities and graphical primitives that are suitable for teaching and assignments. It is not a complete or drop-in replacement for the original ACM libraries.

Usage and purpose
-----------------
- Target audience: students and instructors of the Programació 2 course at EPS, UdL.
- Scope: basic graphics primitives, small geometry utilities, and a couple of helper utilities used in the course exercises and examples.

Building and testing
--------------------
This project uses Gradle as its build system. To build and run tests locally you can use the included Gradle wrapper.

- Build the project:

    ./gradlew build

- Run tests:

    ./gradlew test

License
-------
This project is distributed under the MIT License. See the `LICENSE.txt` file in the repository root for details.

Acknowledgements and references
--------------------------------
- Original inspiration and reference: ACM Java Task Force (ACM Java Libraries) — https://cs.stanford.edu/people/eroberts/jtf/
- This implementation was created for teaching purposes and intentionally keeps the API surface small and simple.


