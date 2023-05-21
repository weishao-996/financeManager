
function loginApi(data) {
  return $axios({
    'url': '/financeManager/user/login',
    'method': 'post',
    'data': data,
    'headers': {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  });
}

function logoutApi(){
  return $axios({
    'url': '/financeManager/user/logout',
    'method': 'post',
  })
}

function register(data){
  return $axios({
    'url': '/financeManager/user/register',
    'method': 'post',
    'data': data,
  })
}

function getUserSelectList(){
  return $axios({
    'url': '/financeManager/user/getUserSelectList',
    'method': 'get',
  })
}


