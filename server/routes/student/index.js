let router = require('express').Router();

router.route('/student/:stuNum').get(function (req, res) {
    let db = req.app.get('db');
    let stuNum = req.params.stuNum;
    db.query("SELECT * FROM student WHERE stuNum = ?", stuNum, function (err, result) {
        if (err) res.status(500).end();
        else if (result.length === 1) {
            let response = {
                "stuNum": result[0].stuNum,
                "name": result[0].name,
                "prize": result[0].prize,
                "pain": result[0].pain
            }
            res.status(200).json(response);
        } else res.status(500).end();
    });
});


module.exports = router;