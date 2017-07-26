let router = require('express').Router();

router.route('/patrol').post(function (req, res) {
    let db = req.app.get('db');
    db.query("UPDATE ROOM SET checked = false", function (err, result) {
        if (err) {
            console.log(err);
            res.status(500).end();
        } else {
            console.log(result.affectedRows);
            res.status(200).end();
        }
    });
});

router.route('/patrol/:roomNum').put(function (req, res) {
    if (typeof req.params.roomNum === "undefined") res.status(400).end();
    else {
        db.query("UPDATE ROOM SET checked = true WHERE roomNum = ?", roomNum, function (err, result) {
            if (err) {
                console.log(err);
                res.status(500).end();
            } else {
                console.log(result.affectedRows);
                res.status(200).end();
            }
        });
    }
});


/**
 * 
 * 해당 층의 방 정보 조회 (순찰 정보)
 */
router.route('/patrol/:floor').get(function (req, res) {
    if (typeof req.params.floor === "undefined") res.status(400).end();
    else {
        let db = req.app.get('db');
        let like = req.params.floor + "%";
        db.query("SELECT roomNum, isAttention, checked FROM ROOM WHERE roomNum like ? ORDER BY roomNum", like,
            function (err, rows) {
                if (err) {
                    res.status(500).end();
                } else {
                    console.log(rows);
                    let response = [];
                    let promise = new Promise(function (resolve, reject) {
                        for (let i = 0; i < rows.length; i++) {

                            db.query("SELECT name FROM student WHERE roomNum = ? ORDER BY name", rows[i].roomNum, function (_err, _rows) {
                                let process = new Promise(function (process_resolve, process_reject) {
                                    console.log(rows[i].isNice);
                                    let room = {
                                        "roomNum": rows[i].roomNum,
                                        "isAttention": rows[i].isAttention == 1 ? true : false,
                                        "checked": rows[i].checked == 1 ? true : false,
                                        "members": []
                                    };
                                    for (let j = 0; j < _rows.length; j++) {
                                        room.members.push(_rows[j].name);
                                    }
                                    response.push(room);
                                    console.log(room);
                                    process_resolve();
                                }).then(() => {
                                    if (i === rows.length - 1) resolve();
                                });
                            });
                        }
                    }).then(() => {
                        res.status(200).json(response);
                    });

                }
            });
    }
});
module.exports = router;