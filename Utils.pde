PVector pentatip(int i, float r, float o) {
  return new PVector( cos( TWO_PI/5 * (i) + o - HALF_PI ) * r,
                      sin( TWO_PI/5 * (i) + o - HALF_PI) * r );
}

PVector pentaside(int i, float r, float o) {
  return new PVector( cos( TWO_PI/5 * (i+0.5) + o - HALF_PI ) * r,
                      sin( TWO_PI/5 * (i+0.5) + o - HALF_PI ) * r );
}

