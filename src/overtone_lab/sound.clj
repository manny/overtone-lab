(ns overtone-lab.sound)
(definst foo [] (saw 200))
(definst bar [freq 200] (saw freq))
(definst baz [freq 440] (* 0.3 (saw freq)))
(bar 100)
(foo)
(baz)
(+ 1 2 3)
(def x 2)

(kill foo)
(kill bar)
(kill baz)
(stop)

(definst quux [freq 440] (* 0.3 (saw freq)))

(definst kick [freq 120 dur 0.3 width 0.5]
  (let [freq-env (* freq (env-gen (perc 0 (* 0.99 dur))))
                     env (env-gen (perc 0.01 dur) 1 1 0 1 FREE)
                     sqr (* (env-gen (perc 0 0.01)) (pulse (* 2 freq) width))
                     src (sin-osc freq-env)
                     drum (+ sqr (* env src))]
    (compander drum drum 0.2 1 0.1 0.01 0.01)))

(kick)

(definst c-hat [amp 0.8 t 0.04]
  (let [env (env-gen (perc 0.001 t) 1 1 0 1 FREE)
        noise (white-noise)
        sqr (* (env-gen (perc 0.01 0.04)) (pulse 880 0.2))
        filt (bpf (+ sqr noise) 9000 0.5)]
    (* amp env filt)))

(def metro (metronome 128))
(metro)
(metro 100)


(defn player [beat]
  (at (metro beat) (kick))
  (at (metro (+ 0.375 beat)) (c-hat))
  (at (metro (+ 0.5  beat)) (kick))
  (at (metro (+ 0.75  beat)) (c-hat))
  (at (metro (+ 0.87  beat)) (c-hat))
  (apply-by (metro (inc beat)) #'player (inc beat) []))

(player (metro))

(metro-bpm metro 40)
(stop)
