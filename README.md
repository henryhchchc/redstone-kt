# Project Redstone

This project contains common components that are used in my software engineering research.
It consists of several modules

[![Build and Test on Push](https://github.com/henryhchchc/redstone/actions/workflows/build-on-push.yml/badge.svg)](https://github.com/henryhchchc/redstone/actions/workflows/build-on-push.yml)

## Reflection

This module help analyze the reflection objects in JVM (e.g., classes, methods, fields, etc.)

### Motivation

JVM reflection API sucks.
My goal is to construct a reflection library that

- The classes are non-global.
  Analysis can be done independently in several scopes.
  Scopes can be created and destroyed any time.
- The objects are serializable.
  The class / method objects can be saved and loaded somewhere else.
- Aware of generics.
  Type parameters and their materialization are available for generic objects.
  Finer-grained type inference is possible.

## Warning

⚠️ This project is in early development stage.
**The stability of the APIs are not the current focus.**
Use at your own risk.
