let router = require('express').Router();
let util = require('../util');
/**
 * 모든 방 목록 조회
 * ::USEAGE::
 [
  {
    "roomNum": 413,
    "isAttention": 1,
    "isNice": 1,
    "members": [
      "윤태훈",
      "홍길동"
    ]
  },
  {
    "roomNum": 414,
    "isAttention": 0,
    "isNice": 1,
    "members": [
      "길똥",
      "후하히"
    ]
  }
]
 */
router.route('/room').get(function (req, res) {

    let response = [];


    let db = req.app.get('db');
    let date = new Date();
    let now = util.getDateNow();

    db.query("SELECT r.roomNum, r.isAttention, (select isNice from CLEANLINESS where date = ? and roomNum = r.roomNum) as isNice " +
        "FROM ROOM as r LEFT JOIN CLEANLINESS as c ON r.roomNum = c.roomNum", now,
        function (err, rows) {
            if (err) res.status(400).end();
            else {
                console.log(rows);
                let response = [];
                let promise = new Promise(function (resolve, reject) {
                    for (let i = 0; i < rows.length; i++) {

                        db.query("SELECT name FROM student WHERE roomNum = ?", rows[i].roomNum, function (_err, _rows) {
                            let process = new Promise(function (process_resolve, process_reject) {
                                console.log(rows[i].isNice);
                                let room = {
                                    "roomNum": rows[i].roomNum,
                                    "isAttention": rows[i].isAttention,
                                    "isNice": rows[i].isNice,
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
        })
});

/**
 * 특정한 방과 그 방을 사용하고 있는 학생의 정보를 가져오는 uri
 * ::USEAGE::
 {
  "roomNum": "413",
  "isNice": true,
  "members": [
    {
      "stuNum": 20214,
      "name": "윤태훈",
      "prize": 1,
      "pain": 1
    },
    {
      "stuNum": 20215,
      "name": "홍길동",
      "prize": 0,
      "pain": 0
    }
  ]
}
 */
router.route('/room/:roomNum').get(function (req, res) {
    if (typeof req.params.roomNum === "undefined") {
        res.status(400).end();
    } else {
        let roomNum = req.params.roomNum;
        let db = req.app.get('db');
        let now = util.getDateNow();
        db.query("SELECT s.name, s.prize, s.pain, s.stuNum, c.isNice  FROM STUDENT as s LEFT JOIN CLEANLINESS as c ON s.roomNum = c.roomNum WHERE s.roomNum = ? and c.date=?", [roomNum, now], function (err, rows) {
            if (err || rows.length == 0) res.status(400).end();
            else {
                let promise = new Promise(function (resolve, reject) {
                    let response = {
                        "roomNum": Number,
                        "isNice": Boolean,
                        "members": []
                    };
                    response.roomNum = roomNum;
                    response.isNice = rows[0].isNice == 1 ? true : false;

                    for (let i = 0; i < rows.length; i++) {

                        let member = {
                            "stuNum": rows[i].stuNum,
                            "name": rows[i].name,
                            "prize": rows[i].prize,
                            "pain": rows[i].pain
                        }
                        response.members.push(member);
                    }
                    resolve(response);
                }).then((response) => {
                    res.status(200).json(response);
                });
            }
        });

    }
})

router.route('/room').get(function (req, res) {

})


module.exports = router;