const refresh = async () => {
  const body = {
    email: `${window.sessionStorage.getItem('email').toString()}`
  }
  const opciones = {
    method: 'POST',
    body: JSON.stringify(body),
    headers: {
      Accept: 'application/json',
      Authorization: `Bearer ${window.sessionStorage.getItem('refresh_token')}`,
      'Content-Type': 'application/json'
    }
  }
  const response = await fetch('http://localhost:8080/token/refresh', opciones)
    .then(res => res.json())
  window.sessionStorage.setItem('access_token', response.data.access_token)
  window.sessionStorage.setItem('refresh_token', response.data.refresh_token)
}

export const helpHttp = () => {
  const customFetch = async (endpint, options) => {
    const defaultHeader = {
      Accept: 'application/json'
    }

    if (!Object.hasOwn(options.headers, 'Authorization')) {
      defaultHeader.Authorization = `Bearer ${window.sessionStorage.getItem('access_token')}`
    }

    options.headers = options.headers ? { ...options.headers, ...defaultHeader } : defaultHeader

    options.body = JSON.stringify(options.body) || false
    if (!options.body) delete options.body

    let response = await fetch(endpint, options).then(res => res.json())
    if (response.statusCode === 403) {
      await refresh()
      options.headers.Authorization = `Bearer ${window.sessionStorage.getItem('access_token')}`
      response = await fetch(endpint, options).then(res => res.json())
    }

    return response
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

  const pat = (url, options = {}) => {
    options.method = 'PATCH'
    return customFetch(url, options)
  }

  return { get, post, put, del, pat }
}
