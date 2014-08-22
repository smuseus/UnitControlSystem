UnitControlSystem
=================

Sound Development City

## TO DO: ##
*	XBEE
*	Simple JSON scene saver and loader
*	BUGSSSZZzz!

## Interaction ##

Press ALT to enter menu mode, a list of commands becomes available through pressing single keys.
Press ENTER to exit menu mode.
(NOTE: you need to press and release ALT, then press the approriate key.)
(NOTE: with 'set id' there still is some issues, press ALT, i, type id, ALT, ENTER)

*	ALT, a : Adds new unit.
*	ALT, d : Deletes selected unit.
*	ALT, i : Enables you to type in the ID (for xbee) of the unit.
*	ALT, m : Sets the Midi controller of the unit, it will detect singal form incoming Midi signals. 

## Classes ##

* 	UnitHandler : owns all the units.

*	Unit : One whistle unit.
	* It owns an Appearance class, which deals with graphical rendering of the unit.
	* I also should own a Controll class, which deals with the xbee serial interface.


*	UserInput : handles all interaction events (Mouse, Key), and triggers operations accordningly. 
*	MidiHandler : handles midi input. (I will probably make this a class owned by UserInput).

## Xbee Setup ##

COORDINATOR config:
  
 *	Reset to factory settings: XBP24BZ7 -> ZigBee Coordinator API -> (Newest)
 *	Put in API mode to: ATAP = 2
 *	Set PAN id to arbitrary value:	ATID = CC
 *	Set the Node Identifier: ATNI = COORDINATOR


 * 	The XBee network will assign the network 16-bit MY address. The coordinator MY address is always 0
 
*  	X-CTU tells SH and SL Addresses
 
 ROUTER DEVICE config:
  
 *	Reset to factory settings: XBP24BZ7 -> ZigBee Router API -> (Newest)	
 *	Put in API mode 2:	ATAP = 2
 *  Set PAN id to arbitrary value:	ATID == 00 (counting, 11, 22, 33, 44 etc..)
 * 	Set the Node Identifier (give it a meaningful name): ATNI = Unit 00

## Current Xbee Addresse ##

Coordinator
64-Bit Address: 13 A2 00 40 B0 E8 FF

Unit 00
64-Bit Address: 13 A2 00 40 99 F0 EE

Unit 11
64-Bit Address: 13 A2 00 40 99 5C 02

Unit 22
64-Bit Address: 13 A2 00 40 9E EC D3

Unit 33
64-Bit Address: 13 A2 00 40 9E EC CD

Unit 44
64-Bit Address: 13 A2 00 40 9E EC 92
