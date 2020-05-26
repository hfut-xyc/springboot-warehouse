import axios from 'axios';

export const postRequest = (url, params) => {
    return axios({
      method: 'post',
      url: url,
      data: params,
      transformRequest: [function (data) {
        let result = '';
        for (let it in data) {
          result += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&';
        }
        // 截去最后多余的一个&
        result = result.substr(0, result.length - 1);
        return result;
      }],
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    });
  }
  