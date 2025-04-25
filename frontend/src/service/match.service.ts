import axios from "axios"
const API_URL = "http://172.18.176.1:8081/api/v1/matches"

const getMatchesByDate = async(date: String) => {
    const response = await axios.get(`${API_URL}/by-date`, {
        params: { date },
    })
    return response.data.data
}

const getMatchById = async(idMatch: number) => {
    const response = await axios.get(`${API_URL}/${idMatch}`)
    return response.data
}

const MatchService = {
    getMatchesByDate, getMatchById
}

export default MatchService;