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