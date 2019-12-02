const express = require('express');
const app = express();
const fuelCalculator = require('./day1/fuelcalculator');
const intcode = require('./day2/intcode');

app.post('/day1/fuelcalculator', fuelCalculator);
app.post('/day2/intcode', intcode);

const port = process.env.PORT || 3000;
app.listen(port, () => 
    console.log(`Server listening on :${port}`)
);
