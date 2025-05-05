const BetForm = () => {
  const teamHomeLogo = `./src/assets/${currentMatch.competition.name
    .toLowerCase()
    .replace(/\s+/g, "")}/${currentMatch.homeTeam.teamName
    .toLowerCase()
    .replace(/\s+/g, "")}.png`;

  const teamAwayLogo = `./src/assets/${currentMatch.competition.name
    .toLowerCase()
    .replace(/\s+/g, "")}/${currentMatch.awayTeam.teamName
    .toLowerCase()
    .replace(/\s+/g, "")}.png`;

  const predictionOptions = ["LOCAL", "EMPATE", "VISITANTE"];
  return (
    <>
      <div>
        <div>
          <h2>Predicción</h2>
          <div>
            <div>
              <label htmlFor="Competition">Competition:</label>
              <p>{currentMatch.competition.name}</p>
            </div>
            <div>
              <label htmlFor="match">Partido:</label>
              <p>
                <img src={teamHomeLogo} alt="homeTeam" />
                {currentMatch.homeTeam.teamName} -
                {currentMatch.homeTeam.teamName}
                <img src={teamAwayLogo} alt="awayTeam" />
              </p>
            </div>
            <div>
              <label htmlFor="date">Fecha:</label>
              <p>{currentMatch.date}</p>
            </div>
          </div>
          <div>
            {predictionOptions.map((key: string, index: number) => {
                return(
                    <div key={index}>
                        {key}
                    </div>
                )
            })}
          </div>
        </div>
      </div>
    </>
  );
};
