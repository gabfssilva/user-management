package br.com.user.management.chains.v1;

import br.com.user.management.chains.Chain;
import br.com.user.management.chains.v1.commands.*;
import org.apache.commons.chain.impl.ChainBase;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public class FlowsProducer {
    @Produces
    @Chain(name = "fetchUserByIdChain")
    @ApplicationScoped
    public ChainBase fetchUserByIdChain(FetchUserByIdCommand fetchUserByIdCommand,
                                    CheckIfUserIsNotNullCommand checkIfUserIsNotNullCommand,
                                    ConvertUserResourceCommand convertUserResourceCommand){
        ChainBase chainBase = new ChainBase();
        chainBase.addCommand(fetchUserByIdCommand);
        chainBase.addCommand(checkIfUserIsNotNullCommand);
        chainBase.addCommand(convertUserResourceCommand);
        return chainBase;
    }

    @Produces
    @Chain(name = "fetchUserAddressByIdChain")
    @ApplicationScoped
    public ChainBase fetchUserByIdChain(FetchUserByIdCommand fetchUserByIdCommand,
                                        SetUserAddressToContextCommand setUserAddressToContextCommand,
                                        CheckIfUserAddressIsNotNullCommand checkIfUserIsNotNullCommand,
                                        ConvertUserAddressResourceCommand convertUserAddressResourceCommand){
        ChainBase chainBase = new ChainBase();
        chainBase.addCommand(fetchUserByIdCommand);
        chainBase.addCommand(setUserAddressToContextCommand);
        chainBase.addCommand(checkIfUserIsNotNullCommand);
        chainBase.addCommand(convertUserAddressResourceCommand);
        return chainBase;
    }


    @Produces
    @Chain(name = "createUserChain")
    @ApplicationScoped
    public ChainBase createUserChain(ConvertUserCommand convertUserCommand,
                                     SaveUserCommand saveUserCommand,
                                     ValidateUserAddressCommand validateUserAddressCommand,
                                     ConvertUserResourceCommand convertUserResourceCommand){
        ChainBase chainBase = new ChainBase();
        chainBase.addCommand(convertUserCommand);
        chainBase.addCommand(validateUserAddressCommand);
        chainBase.addCommand(saveUserCommand);
        chainBase.addCommand(convertUserResourceCommand);
        return chainBase;
    }

    @Produces
    @Chain(name = "updateUserChain")
    @ApplicationScoped
    public ChainBase updateUserChain(ConvertUserCommand convertUserCommand,
                                     ValidateUserAddressCommand validateUserAddressCommand,
                                     UpdateUserCommand updateUserCommand,
                                     FetchUserByIdCommand fetchUserByIdCommand,
                                     ConvertUserResourceCommand convertUserResourceCommand){
        ChainBase chainBase = new ChainBase();
        chainBase.addCommand(convertUserCommand);
        chainBase.addCommand(validateUserAddressCommand);
        chainBase.addCommand(updateUserCommand);
        chainBase.addCommand(fetchUserByIdCommand);
        chainBase.addCommand(convertUserResourceCommand);
        return chainBase;
    }

    @Produces
    @Chain(name = "addAddressToUserChain")
    @ApplicationScoped
    public ChainBase addAddressToUserChain(
                                    ConvertUserAddressCommand convertUserAddressCommand,
                                     ValidateUserAddressCommand validateUserAddressCommand,
                                     AddAddressToUserCommand addAddressToUserCommand,
                                     FetchUserByIdCommand fetchUserByIdCommand,
                                    ConvertUserAddressResourceCommand convertUserAddressResourceCommand){
        ChainBase chainBase = new ChainBase();
        chainBase.addCommand(convertUserAddressCommand);
        chainBase.addCommand(validateUserAddressCommand);
        chainBase.addCommand(addAddressToUserCommand);
        chainBase.addCommand(fetchUserByIdCommand);
        chainBase.addCommand(convertUserAddressResourceCommand);
        return chainBase;
    }

    @Produces
    @Chain(name = "updateUserAddressChain")
    @ApplicationScoped
    public ChainBase updateUserAddressChain(
                                        ConvertUserAddressCommand convertUserAddressCommand,
                                        ValidateUserAddressCommand validateUserAddressCommand,
                                        UpdateUserCommand updateUserCommand,
                                        FetchUserByIdCommand fetchUserByIdCommand,
                                        ConvertUserAddressResourceCommand convertUserAddressResourceCommand){
        ChainBase chainBase = new ChainBase();
        chainBase.addCommand(convertUserAddressCommand);
        chainBase.addCommand(validateUserAddressCommand);
        chainBase.addCommand(updateUserCommand);
        chainBase.addCommand(fetchUserByIdCommand);
        chainBase.addCommand(convertUserAddressResourceCommand);
        return chainBase;
    }

    @Produces
    @Chain(name = "deleteUserChain")
    @ApplicationScoped
    public ChainBase deleteUserChain(MarkUserAsInactiveCommand markUserAsInactiveCommand){
        ChainBase chainBase = new ChainBase();
        chainBase.addCommand(markUserAsInactiveCommand);
        return chainBase;
    }

    @Produces
    @Chain(name = "deleteUserAddressChain")
    @ApplicationScoped
    public ChainBase deleteUserAddressChain(RemoveAddressFromUser removeAddressFromUser){
        ChainBase chainBase = new ChainBase();
        chainBase.addCommand(removeAddressFromUser);
        return chainBase;
    }
}
