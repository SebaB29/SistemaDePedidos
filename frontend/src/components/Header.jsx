import { NavBar } from './NavBar'

const Header = ({ title }) => {
  const styles = {
    backgroundColor: 'rgb(255, 157, 0)'
  }

  return (
    <header style={styles}>
      <h1>{title}</h1>
      <NavBar />
    </header>
  )
}

export default Header
