exports.getDateNow = function () {
    let date = new Date();
    return date.getUTCFullYear() + "-" + (date.getUTCMonth() + 1) + "-" + date.getUTCDate();
}