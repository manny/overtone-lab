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
