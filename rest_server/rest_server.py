#!/usr/bin/env python

from flask import Flask, jsonify, abort, request, Response
import sqlite3
import RPi.GPIO as GPIO
import time

app = Flask(__name__)

GPIO.setmode(GPIO.BCM)

trash = []

NR_SENSORI = 1

TRIG = 23
ECHO = 24

def init():
    # lettura dati sui bidoni da sqlite3 database
    conn = sqlite3.connect('init.db')
    curs = conn.cursor()
    curs.execute("SELECT * FROM trash ORDER BY id")
    tmp = curs.fetchall()
    curs.close()
    conn.close()

    # restituisce una lista di dizionari, ad ogni dizionario corrisponde un bidone
    trash = []
    i = 0
    for t in tmp:
        trash[i] = dict()
        trash[i]['id'] = t[0]
        trash[i]['storeName'] = t[1]
        trash[i]['maxVolume'] = t[2]
        trash[i]['currentVolume'] = -1
        trash[i]['type'] = t[3]
        trash[i]['typeOfWaste'] = t[4]
        i = i + 1

    return trash


# main function:
# 1) polling sui vari sensori
# 2) elaborazione dati
# 3) invio notifiche
# 4) chiamo centrale

@app.route('/')
def start():
    # i dati sui bidoni verranno memorizzati in una lista di dizionari
    trash = init()

    while (i<NR_SENSORI):
        GPIO.setup(TRIG, GPIO.OUT)
        GPIO.setup(ECHO, GPIO.IN)
        GPIO.output(TRIG, False)
        time.sleep(0.00001) #wait 10 microsec
        GPIO.output(TRIG, True)
        time.sleep(0.00001) #wait 10 microsec
        GPIO.output(TRIG, False)
        #echo=1 per tutto il tempo tra l'invio del segnale e il ritorno
        #acquisisco tempo ultimo in cui echo=0
        while GPIO.input(ECHO) == 0:
            pulse_start = time.time()
        # acquisisco tempo ultimo in cui echo=1
        while GPIO.input(ECHO) == 1:
            pulse_end = time.time()
        pulse_duration = pulse_end - pulse_start
        distance = pulse_duration * 17150   #cm
        distance = round(distance, 2)
        print "Distance:", distance, "cm"

        # salva dati relativi al bidone

        GPIO.cleanup()
        i = i+1

    return

# ---------- REST FUNCTION ---------- #

# return the amount of trash in each bin/waste container

@app.route('/api/v1.0/amount', methods=['POST'])
def get_trash():
    id1 = request.form['id1']
    id2 = request.form['id2']
    tmp = []
    for t in trash:
        if( t['id'] == id1 or t['id']==id2 or t['type']=="wastecontainer"):
            tmp.append(t)

    return jsonify({'trash': tmp})


# assign a free colour and light up the stripes

@app.route('/api/v1.0/colour', methods=['POST'])
def colour():
    id = request.form['id']



    return


if __name__ == '__main__':
    app.run(debug=True)