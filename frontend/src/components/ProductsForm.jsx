// import { helpHttp } from '../helpers/helpHttp'

export const ProductsForm = ({ setLoading, setProducts }) => {
  const formStyles = {
    padding: '1rem',
    display: 'flex',
    justifyContent: 'space-around'
  }
  const searchInputStyles = {
    padding: '0.25rem',
    borderRadius: '0.25rem',
    fontSize: '1rem'
  }

  const handleSearch = (e) => {
    e.preventDefault()
    console.log(e.target.search.value)
    // setLoading(true)
    // helpHttp.get(
    //   'products endpoint',
    //   {
    //     body: {
    //       name: e.target.search.value
    //     },
    //     headers: {
    //       'Content-Type': 'Application/json',
    //       Accept: 'application/json'
    //     }
    //   })
    //   .then(res => {
    //     setLoading(false)
    //     setProducts(res)
    //   })
  }

  return (
    <>
      <form onSubmit={handleSearch} style={formStyles}>
        <input
          style={searchInputStyles}
          name='search'
          type='search'
          placeholder='Buscar...'
          autoComplete='off'
        />
        {/* <select name='color'>
        <option value='' selected>Color</option>
        <option value='red'>rojo</option>
        <option value='blue'>azul</option>
      </select>
      <select name='waist'>
        <option value='' selected>Talle</option>
        <option value='small'>pequeño</option>
        <option value='mediun'>mediano</option>
        <option value='large'>grande</option>
      </select>
      <select name='weight'>
        <option value='' selected>Peso</option>
        <option value='red'>liviano</option>
        <option value='red'>pesado</option>
        <option value='blue'>azul</option>
      </select> */}

      </form>
    </>
  )
}
