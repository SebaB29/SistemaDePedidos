import { useState } from 'react'

export const useFrom = (initialForm, validateForm, customFetch, endpint) => {
  const [form, setForm] = useState(initialForm)
  const [errors, setErrors] = useState({})
  const [loading, setLoading] = useState(false)
  const [response, setResponse] = useState(null)

  const handleChange = (e) => {
    const { name, value } = e.target
    const newForm = {
      ...form,
      [name]: value
    }
    setForm(newForm)
    setErrors(validateForm(newForm))
  }

  const handleChangeProp = (e, index) => {
    const { name, value } = e.target
    const nextProps = [...form.attributes]
    if (name === 'description') {
      nextProps.splice(index, 1, { ...form.attributes[index], description: value })
    } else {
      nextProps.splice(index, 1, { ...form.attributes[index], value })
    }
    const newForm = { ...form, attributes: nextProps }
    setForm(newForm)
    setErrors(validateForm(newForm))
  }

  const handleAddPropertie = () => {
    const newForm = {
      ...form,
      attributes: form.attributes.concat({ description: '', value: '' })
    }
    setForm(newForm)
    setErrors(validateForm(newForm))
  }

  const handleBlur = (e) => {
    handleChange(e)
  }

  const handleSubmit = (e, headers = {}, body = form) => {
    e.preventDefault()
    setErrors(validateForm(form))

    if (Object.keys(errors).length === 0) {
      setLoading(true)
      customFetch(
        endpint,
        {
          body,
          headers: {
            ...headers,
            'Content-Type': 'Application/json',
            Accept: 'application/json'
          }
        })
        .then(res => {
          setLoading(false)
          setResponse(res)
          setForm(initialForm)
        })
        .catch(err => {
          setLoading(false)
          setResponse({
            status: '500',
            error: err,
            data: null
          })
        })
    }
  }

  return {
    form,
    errors,
    loading,
    response,
    handleChange,
    handleBlur,
    handleSubmit,
    handleAddPropertie,
    handleChangeProp
  }
}
