/**
 * 
 */
  
 $(function() {
    var $trObj = $('table.table_apply tr.apply');
    var url = '/Add_it/applylist';
    console.log(url);
    $.ajax({
        url : url,
        method : 'get',
        data : {
            id : 'indi7',
            opt : 'getList',
            currentPage : 3,
            field : 'comName',
            query : ''
        },
        success : function(responseData) {
            var state = "";
            console.log('responseData 아래 출력');
            console.log(responseData);

            // 신청내역 리스트 출력
            $(responseData.list).each(function(i, e) {
                console.log(i + ',' + e);
                console.log(i + ',' + e.app_state);
                console.log(i + ',' + e.app_no);
                switch (e.app_state) {
                    case -1:	state = "거절";
                        break;
                    case 0:	state = "대기";
                        break;
                    case 1:	state = "수락";
                        break;
                    default: console.log("예외")
                }
                console.log('e.adv.c.com_name '+e.c.com_name)
                var $copyObj;
                $copyObj = $trObj.clone();
                $copyObj.find('td.app_no').html(e.app_no);
                $copyObj.find('td.adv_no').html(e.adv.adv_no);
                $copyObj.find('td.app_date').html(e.app_date);
                $copyObj.find('td.com_name').html(e.c.com_name);
                $copyObj.find('td.adv_start').html(e.adv.adv_startmonth);
                $copyObj.find('td.adv_end').html(e.adv.adv_endmonth);
                $copyObj.find('td.app_state').html(state);
                $copyObj.show();
                $trObj.parent().append($copyObj);

            });
            // 페이지 번호 처리
            $(responseData).each(function(i, e) {
                console.log(e.currentPage);
                console.log(e.startPage);
                console.log(e.endPage);
                

            });
        }
    });
});
