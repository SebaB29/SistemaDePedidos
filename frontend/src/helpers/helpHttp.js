export const helpHttp = () => {
  const customFetch = (endpint, options) => {
    const defaultHeader = {
      accept: 'application/json'
    }

    const controller = new AbortController()
    options.signal = controller.signal

    options.headers = options.headers ? { ...options.headers, ...defaultHeader } : defaultHeader

    options.body = JSON.stringify(options.body) || false
    if (!options.body) delete options.body

    setTimeout(() => controller.abort(), 3000)

    return fetch(endpint, options)
      .then(res => res.ok
        ? res.json()
        : Promise.reject(Object.assign(new Error(), {
          err: true,
          status: res.status || '00',
          statusText: res.statusText || 'Ocurrió un error'
        })))
      .catch(err => err)
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
