# synthesizer

<img src="https://raw.githubusercontent.com/marekbobrowski/synthesizer/master/doc/gui.png">

A software synthesizer written as a part of my engineer's thesis, using **only Java SE libraries**.

## Main modules
* Two Oscillators With Four Standard Waveforms
* Amplitude Envelope
* Delay Effect
* Reverb Effect
* Output Volume Setting
* Buffer Size Setting

## How to use it?
The notes can be triggered using computer keyboard in a similiar fashion to how it works with instrument plugins in DAWs.
You can also select a MIDI device connected to your computer (*Settings -> MIDI input*) to capture the MIDI note on/off messages.
The settings can be adjusted by clicking on a knob and dragging your mouse up/down.

If you notice any stuttering please increase the buffer size in **Converter.java** class (it's constructor to be exact), this part has not been perfected yet :smile:.

## Some more details about the modules
* Oscillators - for each of the oscillators choose a waveform from sine, triangle, sawtooth and square; setup the pitch shift in semitones and cents; finally adjust the balance between the two oscillators.
* Amplitude Envelope - standard ADSR (attack, decay, sustain, release) envelope.
* Delay Effect - simple feedback delay with *dry/wet* balance and *feedback* amount settings.
* Reverb Effect - Schrodinger inspired reverb with *dry/wet* balance and *decay* factor settings.
* Buffer Size Setting - choose the size of the buffer generated by each of the oscillators (256 - 512 - 1024 - 2048).

## Setting up the project
This should be just about downloading the source code and opening it in any IDE that supports Java. As mentioned above the synthesizer has been written using only standard Java libraries. The application works with JDK 15.0.2.
