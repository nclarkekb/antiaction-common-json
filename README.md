[![Build Status](https://travis-ci.org/nclarkekb/antiaction-common-json.png?branch=master)](https://travis-ci.org/nclarkekb/antiaction-common-json)

antiaction-common-json
======================

Java JSON library

A compact library to un/-marshall JSON streams to/from objects/representations.

## Usage ##

* JSON stream <> JSON structure <> Java objects.
* JSON stream <> Java objects.

Using JSON structures is more flexible depending on ones requirements.
Converting directly between streams and objects is the fastest.

## Support ##

Java types supported:
* Null
* Boolean
* Integer
* Long
* Float
* Double
* BigInteger
* BigDecimal
* String

... in progress.

## Roadmap ##

* Check how json values not mapped are handled.
* Omit null values from json output.
* Add support for Set<..>, Map<..> and List<..>.
* Add support for converters in JSON stream <> Java objects (un)marshalling.
* Add support for more primitive types and their object counterparts.
* Improve code coverage.

## History ##

### version 0.7.0-SNAPSHOT ###

* Added support for byte, Byte, char, Character, java.util.Date and java.sql.Timestamp fields in stream (un)marshaller.
* Added support for forcing nullable of fields directly in the JSONObjectMappings class.
* Added support for overriding ignore annotations directly in the JSONObjectMappings class.
* Added support for mapping interface collection field types.
* Added @JSONInstanceType annotation to support mapping interface collection field types.
* Added support for stream (un)marshalling of List<..> instances.
* Minor additions to JSONObjectMappings to support List<..> (un)marshalling.
* Unit tests and bug fix to JSONObjectMappings/JSONObjectMapping/JSONObjectFieldMapping toString().
* Code coverage at 86.1% -> 82.6%.

### version 0.6.0 ###
Released: 2015-01-24

Work on improving object to/from stream marshaller/unmarshaller.

* Added support for array fields in JSON stream <> Java objects (un)marshalling.
* Structure marshaller/unmarshaller refactored to work without recursion.
* Added support for (un)marshalling top level arrays to/from representations.
* Added support for (un)marshalling top level arrays to/from Java object.
* Code coverage at 81.3%.

### version 0.5.0 ###
Released: 2013-11-16

Work on implementing object to/from stream marshaller/unmarshaller.

* Split the object mapper and structure marshall/unmarshall code into separate classes.
* Started on stream marshall/unmarshal code. (Does not support arrays yet)
* Reorganized unit tests.

### version 0.4.0 ###
Released: 2013-02-08

Work on improving JSON object mapper for array fields.

* Support for array fields in object mapper.
* Improved JSON decoder exceptions including (x, y) in source stream.
* Name annotation to map object field to/from differently named JSON kay.
* NullValues annotation to allow null values in arrays.
* Converter annotation and support for callbacks in object mapper.
* Work in progress for top level array and native collection types object mapping.

### version 0.3.0 ###
Released: 2012-12-11

Work on the Object to JSON structure mapper.

* Recursive Object to JSON structure mapping.
* Pretty encoding option. (Linefeeds, indentation and spaces)

### version 0.2.0 ###
Released: 2012-12-05

Work on the JSON structure to Object mapper.
Comprehensive unit testing of the Object mapper.

* Added some annotations for nullable, ignore and json.
* Added support for byte[] in the JSON String implementation.
* Added javadoc to most of the classes.
* Recursive JSON structure to Object mapping.

### version 0.1.0 ###
Released: 2012-11-20

Main focus on JSON text decoding/encoding.
Comprehensive unit testing of core functionality.
