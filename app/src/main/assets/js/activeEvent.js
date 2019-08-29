(function() {

window.activeEvent = function _activeEvent(data) {
    if("string" === typeof(data)) {
        data = JSON.parse(data);
    }
    if(!data.type) return;
    var ev = document.createEvent('Events');
    ev.initEvent(data.type, true, true);
    ev.data = data.data || '';
    document.dispatchEvent(ev);
}

})();