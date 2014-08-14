PVector pentatip(int i, float r, float o) {
  return new PVector( cos( TWO_PI/5 * (i) + o - HALF_PI ) * r,
                      sin( TWO_PI/5 * (i) + o - HALF_PI) * r );
}

PVector pentaside(int i, float r, float o) {
  return new PVector( cos( TWO_PI/5 * (i+0.5) + o - HALF_PI ) * r,
                      sin( TWO_PI/5 * (i+0.5) + o - HALF_PI ) * r );
}


IntList digits(int i) {
    IntList digits = new IntList();
    while(i > 0) {
        
        digits.append(i % 10 );
        i /= 10;
    }
    return digits;
}

