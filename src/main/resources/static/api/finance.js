function insert(data){
    return $axios({
        'url': '/financeManager/finance/insert',
        'method': 'post',
        'data': data,
    })
}

function update(data){
    return $axios({
        'url': '/financeManager/finance/update',
        'method': 'post',
        'data': data,
    })
}



function deleteFinance(data){
    return $axios({
        'url': '/financeManager/finance/delete',
        'method': 'post',
        'data': data,
    })
}

function history(){
    return $axios({
        'url': '/financeManager/finance/history',
        'method': 'get',
    })
}

function compare(data){
    return $axios({
        'url': '/financeManager/finance/compare',
        'method': 'post',
        'data': data,
    })
}


function financePage(data){
    return $axios({
        'url': '/financeManager/finance/page',
        'method': 'post',
        'data': data,
    })
}