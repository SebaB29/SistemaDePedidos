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
    const nextProps = [...form.properties]
    if (name === 'name') {
      nextProps.splice(index, 1, { ...form.properties[index], name: value })
    } else {
      nextProps.splice(index, 1, { ...form.properties[index], description: value })
    }
    const newForm = { ...form, properties: nextProps }
    setForm(newForm)
    setErrors(validateForm(newForm))
  }

  const handleAddPropertie = () => {
    const newForm = {
      ...form,
      properties: form.properties.concat({ name: '', description: '' })
    }
    setForm(newForm)
    setErrors(validateForm(newForm))
  }

  const handleBlur = (e) => {
    handleChange(e)
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    setErrors(validateForm(form))

    if (Object.keys(errors).length === 0) {
      setLoading(true)
      customFetch(
        endpint,
        {
          body: form,
          headers: {
            'Content-Type': 'Application/json',
            Accept: 'application/json'
          }
        })
        .then(res => {
          setLoading(false)
          setResponse(res)
          setForm(initialForm)
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
