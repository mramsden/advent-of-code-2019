const express = require('express');
const app = express();
const fuelCalculator = require('./day1/fuelcalculator');

app.post('/day1/fuelcalculator', fuelCalculator);

const port = process.env.PORT || 3000;
app.listen(port, () => 
    console.log(`Server listening on :${port}`)
);
