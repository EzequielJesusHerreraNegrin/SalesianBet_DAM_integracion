export const formatDate = (isoDate: string) => {
  const [year, month, day] = isoDate.split("T")[0].split("-");
  const dateFormatted = `${day}/${month}/${year}`;
  return dateFormatted;
};
