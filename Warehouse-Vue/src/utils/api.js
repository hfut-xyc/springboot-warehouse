import axios from 'axios';

export const postRequest = (url, params) => {
    return axios({
      method: 'post',
      url: url,
      data: params,
      transformRequest: [function (data) {
        let result = ''
        for (let it in data) {
          result += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
        }
        return result;
      }],
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    });
  }
  