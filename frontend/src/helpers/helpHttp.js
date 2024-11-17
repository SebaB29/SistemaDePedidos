export const helpHttp = () => {
  const customFetch = (endpint, options) => {
    const defaultHeader = {
      Accept: 'application/json',
      Authorization: `Bearer ${window.sessionStorage.getItem('access_token')}`
    }

    options.headers = options.headers ? { ...options.headers, ...defaultHeader } : defaultHeader

    options.body = JSON.stringify(options.body) || false
    if (!options.body) delete options.body

    // console.log(options.headers)
    // console.log(options.body)
    // console.log(endpint)

    return fetch(endpint, options)
      .then(res => res.json())
  }

  const get = (url, options = {}) => {
    options.method = 'GET'
    return customFetch(url, options)
  }

  const post = (url, options = {}) => {
    options.method = 'POST'
    return customFetch(url, options)
  }

  const put = (url, options = {}) => {
    options.method = 'PUT'
    return customFetch(url, options)
  }

  const del = (url, options = {}) => {
    options.method = 'DELETE'
    return customFetch(url, options)
  }

  return { get, post, put, del }
}
