const fuelForModule = weight => {
    let fuelRequired = 0;
    let fuel = Math.floor(weight / 3) - 2;
    do {
        fuelRequired += fuel;
        fuel = Math.floor(fuel / 3) - 2;
    } while (fuel > 0);
    return fuelRequired;
}

module.exports = (req, res) => {
    let body = [];
    req
        .on('data', chunk => body.push(chunk))
        .on('end', () => {
            const moduleWeights = Buffer.concat(body).toString().split('\n').filter(value => value !== '');
            const fuelRequired = moduleWeights.map(weight => fuelForModule(weight)).reduce((sum, weight) => sum + weight, 0);
            res.status(200).end(fuelRequired.toString());
        });
};
