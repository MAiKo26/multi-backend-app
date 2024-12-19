using AutoMapper;
using dotnet.DTOs;
using dotnet.Models;

namespace dotnet.Mappers;

public class UserMapper : Profile
{
    public UserMapper()
    {
        CreateMap<User, UserDTO>();
    }
}