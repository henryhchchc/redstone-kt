# Reflekt

A JVM reflection library for Kotlin.

## Motivation

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

⚠️ This project is in early development stage.
**The stability of the APIs are not the current focus.**
Use at your own risk.
